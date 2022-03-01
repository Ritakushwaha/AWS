```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
```

```
unzip awscliv2.zip
```

```
sudo ./aws/install
```

```
aws --version
```

```
cd ~/.aws
```

```
cat config
```

```
cat credentials 
```

```
aws --endpoint-url http://localhost:4566 s3 mb s3://rita/bucket_gir
```

```
aws --endpoint-url http://localhost:4566 s3 ls
```