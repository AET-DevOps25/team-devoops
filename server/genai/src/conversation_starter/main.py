from http.server import BaseHTTPRequestHandler, HTTPServer
from prometheus_client import generate_latest, CONTENT_TYPE_LATEST, Counter

# Port for the webserver
PORT = 80

# Example metric
REQUEST_COUNT = Counter('genai_requests_total', 'Total HTTP requests to GenAI service', ['endpoint'])

# Basic webserver will return "Hello World!" when accessed
class Webserver(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/metrics':
            REQUEST_COUNT.labels(endpoint='metrics').inc()
            self.send_response(200)
            self.send_header("Content-type", CONTENT_TYPE_LATEST)
            self.end_headers()
            self.wfile.write(generate_latest())
        else:
            REQUEST_COUNT.labels(endpoint='root').inc()
            self.send_response(200)
            self.send_header("Content-type", "text/plain")
            self.end_headers()
            self.wfile.write(b"Hello World! Welcome to the GenAI Service!\n")

# Main function available via meetatmensa-start-genai script
def start_genai():

    # Start http server and serve until stopped
    with HTTPServer(("", PORT), Webserver) as httpd:
        print(f"Serving on port {PORT}")
        httpd.serve_forever()