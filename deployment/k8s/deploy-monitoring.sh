#!/bin/bash

# Meet@Mensa Monitoring Stack Deployment Script
# This script deploys the complete monitoring stack without CRDs

set -e

# Configuration
NAMESPACE=${1:-"meetatmensa"}
RELEASE_NAME=${2:-"meetatmensa"}
VALUES_FILE=${3:-"values.yaml"}

echo "🚀 Deploying Meet@Mensa monitoring stack..."
echo "Namespace: $NAMESPACE"
echo "Release name: $RELEASE_NAME"
echo "Values file: $VALUES_FILE"

# Check if namespace exists, create if not
if ! kubectl get namespace "$NAMESPACE" >/dev/null 2>&1; then
    echo "📦 Creating namespace $NAMESPACE..."
    kubectl create namespace "$NAMESPACE"
fi

# Update Helm dependencies
echo "📋 Updating Helm dependencies..."
helm dependency update

# Install/upgrade the release
echo "🔧 Installing/upgrading Helm release..."
helm upgrade --install "$RELEASE_NAME" . \
    --namespace "$NAMESPACE" \
    --values "$VALUES_FILE" \
    --wait \
    --timeout 10m

# Wait for pods to be ready
echo "⏳ Waiting for pods to be ready..."
kubectl wait --for=condition=ready pod -l app.kubernetes.io/instance="$RELEASE_NAME" \
    --namespace "$NAMESPACE" \
    --timeout=300s

# Display deployment status
echo "✅ Deployment completed!"
echo ""
echo "📊 Monitoring Stack Status:"
kubectl get pods -n "$NAMESPACE" -l app.kubernetes.io/instance="$RELEASE_NAME"

echo ""
echo "🌐 Services:"
kubectl get svc -n "$NAMESPACE" -l app.kubernetes.io/instance="$RELEASE_NAME"

echo ""
echo "💾 Persistent Volumes:"
kubectl get pvc -n "$NAMESPACE" -l app.kubernetes.io/instance="$RELEASE_NAME"

echo ""
echo "🔗 Access URLs (port-forward):"
echo "Prometheus: kubectl port-forward svc/$RELEASE_NAME-prometheus 9090:9090 -n $NAMESPACE"
echo "Grafana:    kubectl port-forward svc/$RELEASE_NAME-grafana 3000:80 -n $NAMESPACE"
echo "Loki:       kubectl port-forward svc/$RELEASE_NAME-loki 3100:3100 -n $NAMESPACE"

echo ""
echo "🔑 Grafana credentials: admin/admin"
echo "📖 For more information, see README.md" 