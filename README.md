# dynamic-categorization
Dynamic Categorization is a web service that allows social network websites to dynamically categorize their users to groups based on user activity.
Social network providers can define groups and specify requirements that have to be fulfilled for users to be added to those groups.
Then they send all the user activity events to Dynamic Categorization via Kafka. The events are checked against the requirements of all the groups.
These requirements are sets of key-value conditions, where values have to be equal or parts of the JSON activity payload for the given condition key. Nested conditions are also allowed.
Groups are cached for better performance. The system is also designed to be able to reuse subgroups for multiple groups.
Requirements can also be defined so they are re-checked. This way, we can e.g. remove the users that were previously assigned to a "Cracow, Poland" subgroup if they move to another city.
