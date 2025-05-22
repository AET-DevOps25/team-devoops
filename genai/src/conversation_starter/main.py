from http.server import BaseHTTPRequestHandler, HTTPServer

# Port for the webserver
PORT = 80

# Basic webserver will return "Hello World!" when accessed
class Webserver(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-type", "text/plain")
        self.end_headers()
        self.wfile.write(b"Hello World!")

# Main function available via meetatmensa-start-genai script
def start_genai():

    # Start http server and serve until stopped
    with HTTPServer(("", PORT), Webserver) as httpd:
        print(f"Serving on port {PORT}")
        httpd.serve_forever()