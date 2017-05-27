# dynamic-categorization
Dynamic Categorization is a web service that allows social network websites to dynamically categorize their users to groups based on user activity.

Social network providers can define groups and specify requirements that have to be fulfilled for users to be added to those groups.

All the user activity events are sent from the provider to Dynamic Categorization via Kafka. The events are checked against the requirements of all the groups.

These requirements are sets of key-value conditions, where for each key its value has to be equal to or contained in the value of the JSON activity payload for the given key.

Groups are cached for better performance. The system is also designed to be able to reuse subgroups for many groups.

Requirements can also be defined as re-checked. This way, we can e.g. remove the users that were previously assigned to a "Cracow, Poland" subgroup if they move to another city.
