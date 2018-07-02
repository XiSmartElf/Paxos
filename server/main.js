//How to RUN this:
//node main.js, port will be 8080 at localhost.
//issue first POST API
//          example: http://localhost:8080/message. JSON body {"original":"<your string>"}
//                  result will be {"digest":"<your returned SH256 hash>"}
//
//issue second GET API
//          example: http://localhost:8080/message/<the hash you have>
//                  result will be {"original":"<your original string>"}
//
//if issue GET with a unknown hash, you will get 404 for that string.

//*** Performance Question ***
// As more messages are hashed and cached, memory usage can go really high.
// As more user are requesting, server can also be overloaded
// potential solution :
// Partition your service by setting up a proxy to hash the string to direct the request go to specific server for processing
// so that multiple shards are spliting the loads (compute+memory). In memory data can also be either sweep to disk or a external db/storage. 
// only caching the hotest data for each shard.

'use strict'

var express = require('express');
var app = express();
const crypto = require('crypto');

var bodyParser = require('body-parser');
app.use(bodyParser.json());

//caching hash.
var map = {};

//api for getting the hash of a string and storing the mapping in memory.
app.post('/message', function (req, res) {
   console.log(req.body.original);
   const secret = 'abcdefg';
   const hash = crypto.createHmac('sha256', secret)
                   .update(req.body.original)
                   .digest('hex');
   map[hash] = req.body.original;    
   console.log(map);           
   res.send({digest: hash});
})

//api for getting the original string from the hash(from the in memory mapping.)
app.get('/message/:hash', function (req, res) {
   console.log(req.params.hash);
   console.log(map);
   if(!map[req.params.hash]) res.sendStatus(404);
   res.send({original:map[req.params.hash]});
})

var server = app.listen(8080, function () {
  console.log("server up at 8080");
})