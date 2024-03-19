# Search Autocomplete System

Implemented a scalable search autocomplete system optimized for suggesting top k queries to users in constant time.

## System Overview

The system employs a custom Trie (prefix tree) data structure to optimize prefix-based search operations. As users input queries, 
the Trie dynamically processes the input, enabling real-time query suggestions. Additionally, user-submitted queries are logged 
and aggregated weekly, ensuring the availability of relevant suggestions for improved search results.


## System Design
Developed application following system design best practices to ensure a reliable system that is able to scale as traffic 
increases.

### Batch Processing
User submitted queries are logged and aggregated to permanent storage once per week through the use of **batch processing**.



## Technologies
- Java
- Spring Boot
- Batch Processing
- MongoDB
- Redis Cache
- React JS
