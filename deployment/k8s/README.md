# Meet@Mensa Kubernetes Deployment

This directory contains the Helm charts for deploying the Meet@Mensa application on Kubernetes, including a complete monitoring stack without CRDs.

## Architecture

The deployment consists of:

### Application Components

- **client**: React frontend application
- **gateway**: API Gateway service
- **user**: User management service
- **matching**: Matching algorithm service
- **genai**: AI conversation starter service

### Monitoring Stack (No CRDs Required)

- **Prometheus**: Metrics collection and storage
- **Grafana**: Monitoring dashboards and visualization
- **Loki**: Log aggregation and storage
- **Promtail**: Log collection agent (DaemonSet)

## Prerequisites

- Kubernetes cluster (1.19+)
- Helm 3.x
- kubectl configured to access your cluster
- Storage class available (optional, will use default if not specified)

## Installation

1. **Update Helm dependencies:**

   ```bash
   helm dependency update
   ```

2. **Install the application:**

   ```bash
   helm install meetatmensa . -n your-namespace --create-namespace
   ```

3. **For production deployment:**
   ```bash
   helm install meetatmensa . -n production --create-namespace -f values-production.yaml
   ```

## Monitoring Stack Details

### Prometheus

- **Port**: 9090
- **Storage**: 10Gi persistent volume
- **Configuration**: Auto-discovers pods with `prometheus.io/scrape=true` annotation
- **Metrics**: Collects metrics from all application components

### Grafana

- **Port**: 80 (internal), 3000 (container)
- **Storage**: 5Gi persistent volume
- **Default credentials**: admin/admin
- **Data sources**:
  - Prometheus (default)
  - Loki (for logs)

### Loki

- **Port**: 3100
- **Storage**: 10Gi persistent volume
- **Configuration**: Single instance with filesystem storage
- **Log retention**: Configurable via values

### Promtail

- **Deployment**: DaemonSet (runs on all nodes)
- **Port**: 9080
- **Configuration**: Collects logs from all pods with app labels
- **RBAC**: Includes necessary permissions for pod discovery

## Accessing Monitoring Tools

### Port Forwarding

```bash
# Prometheus
kubectl port-forward svc/meetatmensa-prometheus 9090:9090 -n your-namespace

# Grafana
kubectl port-forward svc/meetatmensa-grafana 3000:80 -n your-namespace

# Loki
kubectl port-forward svc/meetatmensa-loki 3100:3100 -n your-namespace
```

### Ingress (if configured)

- Prometheus: `http://prometheus.your-domain.com`
- Grafana: `http://grafana.your-domain.com`

## Configuration

### Customizing Values

Create a custom values file:

```yaml
# values-custom.yaml
prometheus:
  storage:
    persistentVolumeClaim:
      size: 20Gi
      storageClass: "fast-ssd"

grafana:
  adminPassword: "your-secure-password"
  storage:
    persistentVolumeClaim:
      size: 10Gi
```

### Application Metrics

To enable metrics collection for your application pods, add these annotations:

```yaml
metadata:
  annotations:
    prometheus.io/scrape: "true"
    prometheus.io/port: "8080"
    prometheus.io/path: "/metrics"
```

### Log Collection

Promtail automatically collects logs from pods with the `app` label. Ensure your application pods have proper labels:

```yaml
metadata:
  labels:
    app: your-app-name
    component: your-component-name
```

## Storage

The monitoring stack uses persistent volumes for data storage:

- **Prometheus**: 10Gi for metrics data
- **Grafana**: 5Gi for dashboards and configuration
- **Loki**: 10Gi for log data

If no storage class is specified, the default storage class will be used.

## Security Considerations

1. **Change default passwords** in production
2. **Use secrets** for sensitive configuration
3. **Configure network policies** to restrict access
4. **Enable RBAC** (already configured for Promtail)

## Troubleshooting

### Check Pod Status

```bash
kubectl get pods -n your-namespace -l app.kubernetes.io/instance=meetatmensa
```

### View Logs

```bash
# Prometheus logs
kubectl logs -n your-namespace deployment/meetatmensa-prometheus

# Grafana logs
kubectl logs -n your-namespace deployment/meetatmensa-grafana

# Loki logs
kubectl logs -n your-namespace deployment/meetatmensa-loki

# Promtail logs
kubectl logs -n your-namespace daemonset/meetatmensa-promtail
```

### Check Services

```bash
kubectl get svc -n your-namespace -l app.kubernetes.io/instance=meetatmensa
```

### Verify Storage

```bash
kubectl get pvc -n your-namespace -l app.kubernetes.io/instance=meetatmensa
```

## Upgrading

```bash
helm upgrade meetatmensa . -n your-namespace
```

## Uninstalling

```bash
helm uninstall meetatmensa -n your-namespace
```

**Note**: This will remove all data. To preserve data, delete the PVCs manually after uninstalling.

## Customization

Each component can be customized independently by modifying the respective values in the main `values.yaml` file or by creating component-specific value files.
