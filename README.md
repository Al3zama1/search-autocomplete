# Search Autocomplete System

Implemented a scalable search autocomplete system optimized for suggesting top k queries to users in constant time based on the user query input prefix.

## Overview
The autocomplete system utilizes a custom Trie (prefix tree) data structure 
for efficient prefix-based search operations. A caching mechanism was added to the Trie structure to store and retrieve the top k next
most promising query suggestions at each prefix node, significantly reducing response times at the cost of extra storage consumption.
Furthermore, the system makes use of batch processing to aggregate weekly query log files to a permanent MongoDB databases. 
These aggregated logs are then used to construct a new Trie structure once per week, guaranteeing relevant query suggestions.




## Technologies
- Java
- Spring Boot
- Batch Processing
- MongoDB
- Redis Cache
- React JS
