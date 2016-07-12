Auth flow
---------------

1. Get Code Grant

http://localhost:9999/uaa/oauth/authorize?response_type=code&client_id=intellidev&redirect_uri=http://example.com&scope=webshop&state=97536

Use: user/password
Note: The state parameter should be set to a random value in the request and checked on the response for preventing cross-site request forgery

http://example.com/?code=if8AiY&state=97536

Take the code parameter from the redirect URL you got as the response and store it in an environment variable:

CODE=if8AiY

2. Now act as the secure web server and use the code grant to get the access token:

curl intellidev:intellidevsecret@localhost:9999/uaa/oauth/token -d grant_type=authorization_code -d client_id=intellidev -d redirect_uri=http://example.com -d code=7iAVpS -s
 
{"access_token":"e146677a-14a8-4331-9420-8db3f0f2ba54","token_type":"bearer","refresh_token":"b92a37fe-573e-4fd5-b545-5f9573e768ef","expires_in":42764,"scope":"webshop"}

3.  
 