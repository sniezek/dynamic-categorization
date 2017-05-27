package dc.group;

import dc.group.model.GroupConditionKey;
import dc.group.model.GroupSubcondition;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GroupConditionAnalyzer {
    static boolean shouldGroupConditionBeCheckedForActivity(GroupConditionKey key, Map<String, ?> activityPayload) {
        return key.getAnyOf().stream()
                .anyMatch(subcondition -> allSubconditionFieldsInActivityPayload(subcondition, activityPayload));
    }

    static boolean isGroupConditionMetForActivity(GroupConditionKey key, Map<String, ?> activityPayload) {
        return key.getAnyOf().stream()
                .anyMatch(subcondition -> areEqualFieldConditionsMet(subcondition, activityPayload) && areContainFieldConditionsMet(subcondition, activityPayload));
    }

    private static boolean allSubconditionFieldsInActivityPayload(GroupSubcondition subcondition, Map<String, ?> activityPayload) {
        return Stream.of(subcondition.getEqual(), subcondition.getContain())
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .allMatch(activityPayload::containsKey);
    }

    private static boolean areEqualFieldConditionsMet(GroupSubcondition subcondition, Map<String, ?> activityPayload) {
        return areFieldConditionsMet(subcondition.getEqual(),
                activityPayload,
                (cond, activ) -> {
                    Set<?> setOfAllowedValues = new HashSet<>(cond.getValue());
                    Object activityValueOfCondKey = activ.get(cond.getKey());

                    return setOfAllowedValues.contains(activityValueOfCondKey) ||
                            activityValueOfCondKey != null && setOfAllowedValues.stream()
                                    .map(Object::toString)
                                    .anyMatch(val -> val.equalsIgnoreCase(activityValueOfCondKey.toString()));
                }
        );
    }

    private static boolean areContainFieldConditionsMet(GroupSubcondition condition, Map<String, ?> activityPayload) {
        return areFieldConditionsMet(condition.getContain(),
                activityPayload,
                (cond, activ) -> {
                    Object activityValueOfCondKey = activ.get(cond.getKey());

                    return activityValueOfCondKey != null &&
                            new HashSet<>(cond.getValue()).stream()
                                    .map(Object::toString)
                                    .map(String::toLowerCase)
                                    .anyMatch(val -> activityValueOfCondKey.toString().toLowerCase().matches(".*\\b" + val + "\\b.*"));
                }
        );
    }

    private static boolean areFieldConditionsMet(Map<String, Set<?>> conditions, Map<String, ?> activity, BiPredicate<Map.Entry<String, Set<?>>, Map<String, ?>> predicate) {
        Map<String, Set<?>> conditionsToCheck = getFieldConditionsToCheck(conditions, activity);

        if (conditionsToCheck.isEmpty()) {
            return true;
        }

        Map<String, Set<?>> nestedConditionsToCheck = getNestedFieldConditionsToCheck(conditionsToCheck);

        conditionsToCheck = conditionsToCheck.entrySet().stream()
                .filter(entry -> !nestedConditionsToCheck.containsKey(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<String, Set<?>> condition : conditionsToCheck.entrySet()) {
            if (!predicate.test(condition, activity)) {
                return false;
            }
        }

        Map<String, ?> nestedActivityFieldsToCheck = getNestedActivityFieldsToCheck(getActivityFieldsToCheck(conditions, activity));

        for (Map.Entry<String, Set<?>> condition : nestedConditionsToCheck.entrySet()) {
            if (nestedActivityFieldsToCheck.containsKey(condition.getKey())) {
                if (!areFieldConditionsMet((Map<String, Set<?>>) condition.getValue().stream().findFirst().get(), (Map<String, ?>) nestedActivityFieldsToCheck.get(condition.getKey()), predicate)) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    private static Map<String, Set<?>> getFieldConditionsToCheck(Map<String, Set<?>> conditions, Map<String, ?> activity) {
        Map<String, Set<?>> conditionsToCheck = new HashMap<>(conditions);
        conditionsToCheck.keySet().retainAll(activity.keySet());

        return conditionsToCheck;
    }

    private static Map<String, ?> getActivityFieldsToCheck(Map<String, Set<?>> conditions, Map<String, ?> activity) {
        Map<String, ?> activityFieldsToCheck = new HashMap<>(activity);
        activityFieldsToCheck.keySet().retainAll(conditions.keySet());

        return activityFieldsToCheck;
    }

    private static Map<String, Set<?>> getNestedFieldConditionsToCheck(Map<String, Set<?>> conditionsToCheck) {
        return conditionsToCheck.entrySet().stream()
                .filter(entry -> new HashSet<>(entry.getValue()).stream().findFirst().isPresent())
                .filter(entry -> new HashSet<>(entry.getValue()).stream().findFirst().get() instanceof Map)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue())));
    }

    private static Map<String, ?> getNestedActivityFieldsToCheck(Map<String, ?> activityFieldsToCheck) {
        return activityFieldsToCheck.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Map)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private GroupConditionAnalyzer() {
    }
}
