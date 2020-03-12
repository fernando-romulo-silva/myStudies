set -m

/entrypoint.sh couchbase-server &

sleep 15

curl -v -X POST http://127.0.0.1:8091/pools/default -d memoryQuota=300 -d indexMemoryQuota=300
curl -v http://127.0.0.1:8091/node/controller/setupServices -d services=kv%2Cn1ql%2Cindex
curl -v http://127.0.0.1:8091/settings/web -d port=8091 -d username=Administrator -d password=password
curl -v -u Administrator:password -X POST http://127.0.0.1:8091/pools/default/buckets -d name=books -d bucketType=couchbase -d ramQuotaMB=200 -d authType=sasl
sleep 10
curl -v http://127.0.0.1:8093/query/service -d 'statement=create primary index on books'

fg 1

