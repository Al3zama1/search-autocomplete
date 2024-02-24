db.query_logs.findOne(
  { "query": "testing", "weeklyEntries.weekOf": ISODate("2024-02-19T08:00:00.000Z") },
  { "weeklyEntries.$": 1 }
);



db.query_logs.find({
  $and: [{ 
    query: {$eq: 'testing'}
  }, {
    'weeklyEntries.weekOf': {$eq: ISODate("2024-02-26T08:00:00.000Z")}
  }]
},
{
  query: 1,
  totalQueries: 1,
  'weeklyEntries.$': 1}
)


db.query_logs.find({
  $and: [{ 
    query: {$eq: 'testing'}
  }, {
    'weeklyEntries.weekOf': {$eq: ISODate("2024-02-26T08:00:00.000Z")}
  }]
},
{'weeklyEntries.$': 1}
).explain('executionStats')


db.query_logs.findOneAndUpdate(
  {
    $and: [
      { query: { $eq: 'testing' } },
      { 'weeklyEntries.weekOf': { $eq: ISODate("2024-02-26T08:00:00.000Z") } }
    ]
  },
  {
    $inc: {
      totalQueries: 1,
      'weeklyEntries.$.queryCount': 1
    }
  },
  { 
    returnOriginal: false,
    projection: { _id: 1 } 
  }
);






db.query_logs.updateOne(
  { "query": 'testing', "weeklyEntries.weekOf": ISODate("2024-02-26T08:00:00.000Z") },
  { 
    "$inc": { 
      "weeklyEntries.$.queryCount": 1,
      totalQueries: 1
    } 
  }
);