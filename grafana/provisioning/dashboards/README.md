# Meet@Mensa Grafana Dashboards Documentation

This directory contains Grafana dashboards for monitoring the Meet@Mensa microservices application. Each dashboard is designed to provide specific insights into different aspects of the system.

**Business Request**: Any HTTP/API request that directly relates to core business functionality (e.g., creating match requests, user registration, GenAI interactions) - distinct from technical requests like health checks.

## Dashboard Overview

### 1. **Meet@Mensa Overview** (`meetatmensa-overview.json`)
**Purpose**: High-level system overview for executive and operational monitoring

**Key Metrics Monitored**:
- **Request Rate (req/sec)**: Total requests per second across all services
  - *Why monitor*: Indicates system load and user activity
  - *Alert threshold*: >80 req/sec (red), >100 req/sec (yellow)
- **Active Requests**: Number of concurrent requests being processed
  - *Why monitor*: System load and capacity utilization
  - *Alert threshold*: >100 active requests (warning)
- **Memory Usage**: Memory consumption across services
  - *Why monitor*: Resource utilization and potential memory leaks
  - *Alert threshold*: >80% (warning), >90% (critical)
- **Active Threads**: Number of active threads per service
  - *Why monitor*: Thread pool utilization and performance

**Alerts Configured**:
- `ServiceDown`: Triggers when any service is down for >1 minute
- `HighErrorRate`: Triggers when error rate >5% for 5 minutes
- `CriticalErrorRate`: Triggers when error rate >10% for 3 minutes

---

### 2. **Meet@Mensa Application** (`meetatmensa-application.json`)
**Purpose**: Business-focused metrics and application performance

**Key Metrics Monitored**:
- **Business Request Rate**: Requests per second for business operations
  - *Why monitor*: Track user engagement and feature usage
- **Active Business Requests**: Number of concurrent business operations
  - *Why monitor*: Business activity and system load
- **Database Connections by Service**: Active database connections per service
  - *Why monitor*: Database connection pool utilization
- **Database Idle Connections by Service**: Idle database connections per service
  - *Why monitor*: Connection pool efficiency and resource management

**Alerts Configured**:
- `LowRequestRate`: Triggers when request rate <0.1 req/sec for 10 minutes
- `HighActiveRequests`: Triggers when active requests >100 for 5 minutes

---

### 3. **Meet@Mensa Microservices** (`meetatmensa-microservices.json`)
**Purpose**: Detailed service-level monitoring and troubleshooting

**Key Metrics Monitored**:
- **Request Rate by Service**: Individual service request rates
  - *Why monitor*: Identify bottlenecks and service-specific issues
- **JVM Memory Usage (%)**: Java Virtual Machine memory utilization per service
  - *Why monitor*: Memory leaks and resource management
- **Active Threads**: Number of active threads per service
  - *Why monitor*: Thread pool utilization and performance
- **Database Active Connections**: Active database connections per service
  - *Why monitor*: Database performance and connection management

**Alerts Configured**:
- `HighMemoryUsage`: Triggers when memory usage >80% for 10 minutes
- `CriticalMemoryUsage`: Triggers when memory usage >90% for 5 minutes
- `DatabaseConnectionPoolExhausted`: Triggers when pool usage >80% for 5 minutes
- `DatabaseConnectionPoolFull`: Triggers when pool usage >95% for 2 minutes

---

### 4. **Meet@Mensa Logs** (`meetatmensa-logs.json`)
**Purpose**: Centralized log analysis and error tracking

**Key Metrics Monitored**:
- **Error and Warning Rate**: Log-based error and warning frequency
  - *Why monitor*: Proactive error detection and debugging
- **Error and Warning Logs**: Detailed error and warning log entries
  - *Why monitor*: Identify problematic services and error patterns
- **Info Logs**: Information-level log entries
  - *Why monitor*: General system activity and debugging

**Data Source**: Loki (log aggregation)
**Query Examples**:
- `sum(rate({job="meetatmensa-app"} | json | level="ERROR" [5m])) by (container_name)`
- `sum(rate({job="meetatmensa-app"} | json | level="WARN" [5m])) by (container_name)`

---

### 5. **Meet@Mensa Custom Metrics** (`meetatmensa-custom-metrics.json`)
**Purpose**: Business-specific metrics and KPIs

**Key Metrics Monitored**:
- **Total Users Created**: Cumulative user registrations
  - *Why monitor*: Business growth and user acquisition
  - *Metric name*: `users_total`
- **Total Match Requests Created**: Cumulative match requests
  - *Why monitor*: Core business activity and user engagement
  - *Metric name*: `match_requests_total`
- **Total GenAI Requests**: Cumulative AI-powered conversation starter requests
  - *Why monitor*: Feature adoption and AI service performance
  - *Metric name*: `genai_requests_total`
- **Registration and Request Rates**: User registration and match request rates over time
  - *Why monitor*: Business activity trends and user engagement
- **GenAI Request Rate**: AI service requests per time period
  - *Why monitor*: AI feature usage patterns and performance

**Note**: These metrics require custom implementation in the application code.

---

### 6. **Sample Dashboard** (`sample-dashboard.json`)
**Purpose**: Template and example dashboard for reference

**Key Metrics Monitored**:
- **New panel**: Example panel for testing and learning
  - *Why monitor*: Template for creating new dashboard panels

**Usage**: 
- Reference for creating new dashboards
- Testing dashboard configurations
- Learning Grafana dashboard structure

---

## Alert Strategy

### Alert Severity Levels

1. **Critical (Red)**: Immediate action required
   - Service down
   - High error rates (>10%)
   - Critical response times (>2s)
   - Memory usage >90%

2. **Warning (Yellow)**: Attention needed
   - High error rates (>5%)
   - High response times (>1s)
   - Memory usage >80%
   - Database connection pool >80%

3. **Info (Blue)**: Monitoring
   - Low request rates
   - High active requests

### Alert Response Actions

**Service Down**:
1. Check container status: `docker ps`
2. Check service logs: `docker logs <container>`
3. Restart service if needed: `docker-compose restart <service>`

**High Error Rate**:
1. Check application logs in Grafana
2. Review recent deployments
3. Check database connectivity
4. Scale service if needed

**High Response Time**:
1. Check CPU and memory usage
2. Review database performance
3. Check network connectivity
4. Consider service scaling

**Memory Issues**:
1. Check for memory leaks
2. Review garbage collection logs
3. Increase memory limits if needed
4. Restart service if critical

---

## Data Sources

### Prometheus
- **Purpose**: Metrics collection and storage
- **URL**: `http://prometheus:9090`
- **Metrics**: System metrics, application metrics, custom business metrics

### Loki
- **Purpose**: Log aggregation and querying
- **URL**: `http://loki:3100`
- **Data**: Application logs, error logs, access logs

---

## Dashboard Refresh and Updates

### Automatic Refresh
- Dashboards refresh every 30 seconds by default
- Time range: Last 1 hour (configurable)

### Manual Updates
To apply dashboard changes:
```bash
# Restart Grafana container
docker-compose -f deployment/docker/compose.yml restart grafana

# Or restart the entire stack
docker-compose -f deployment/docker/compose.yml down
docker-compose -f deployment/docker/compose.yml up -d
```

### Dashboard Provisioning
Dashboards are automatically provisioned from JSON files in this directory. Changes to JSON files require a Grafana restart to take effect.

---

## Troubleshooting

### No Data in Dashboards
1. **Check Prometheus targets**: Visit `http://localhost:9090/targets`
2. **Verify service endpoints**: Check `/actuator/prometheus` endpoints
3. **Check time range**: Ensure dashboard time range includes data
4. **Verify metric names**: Use Prometheus UI to search for metrics

### Missing Custom Metrics
1. **Check application code**: Ensure metrics are properly implemented
2. **Verify metric names**: Match exact names in dashboard queries
3. **Check Prometheus configuration**: Ensure services are scraped
4. **Restart services**: Reload metrics after code changes

### Log Issues
1. **Check Promtail configuration**: Verify log collection setup
2. **Check Loki connectivity**: Ensure Loki is running and accessible
3. **Verify log format**: Ensure logs are in expected JSON format
4. **Check container logs**: Verify logs are being generated

---

## Best Practices

1. **Monitor Key Business Metrics**: Focus on metrics that impact user experience
2. **Set Appropriate Thresholds**: Base thresholds on historical data and SLAs
3. **Use Meaningful Alert Messages**: Include actionable information in alerts
4. **Regular Dashboard Reviews**: Update dashboards based on changing needs
5. **Document Changes**: Update this README when adding new metrics or alerts

---

## Contact

For questions about monitoring setup or dashboard configuration, refer to the team documentation or contact the DevOps team. 