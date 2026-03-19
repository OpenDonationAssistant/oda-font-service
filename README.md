# ODA Font Service
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/OpenDonationAssistant/oda-font-service)

## Running with Docker

The Docker image is published to GitHub Container Registry. Pull and run with:

```bash
docker run -d \
  --name oda-font-service \
  -e JDBC_URL=jdbc:postgresql://<postgres-host>:5432/<database>?currentSchema=fonts \
  -e JDBC_USER=<db-username> \
  -e JDBC_PASSWORD=<db-password> \
  -e INFINISPAN_HOST=<infinispan-host> \
  -e MINIO_ENDPOINT=<minio-endpoint> \
  -e MINIO_USERNAME=<minio-username> \
  -e MINIO_PASSWORD=<minio-password> \
  ghcr.io/opendonationassistant/font-service:latest
```

### Required Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `JDBC_URL` | PostgreSQL JDBC connection URL | `jdbc:postgresql://localhost:5432/postgres?currentSchema=fonts` |
| `JDBC_USER` | Database username | `postgres` |
| `JDBC_PASSWORD` | Database password | `postgres` |
| `INFINISPAN_HOST` | Infinispan cache host | `127.0.0.1` |
| `MINIO_ENDPOINT` | MinIO object storage endpoint | `http://localhost:9000` |
| `MINIO_USERNAME` | MinIO access key | `minioadmin` |
| `MINIO_PASSWORD` | MinIO secret key | `minioadmin` |


