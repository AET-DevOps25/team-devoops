provider "aws" {
  region     = var.aws_region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
  token      = var.aws_session_token
}

# Create a security group
resource "aws_security_group" "app" {
  name        = "app-sg-${formatdate("YYYYMMDDHHmmss", timestamp())}"
  description = "Security group for application"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "app-sg"
  }
}

# Create an EC2 instance
resource "aws_instance" "app" {
  ami           = var.ami_id
  instance_type = var.instance_type
  key_name      = "vockey"  # Use the existing vockey key pair

  vpc_security_group_ids = [aws_security_group.app.id]

  tags = {
    Name = "app-instance"
  }
}

data "aws_eip" "meetatmensa" {
  filter {
    name   = "tag:Name"
    values = ["meetatmensa-ip"]
  }
}

resource "aws_eip_association" "meetatmensa" {
  instance_id   = aws_instance.app.id
  allocation_id = data.aws_eip.meetatmensa.id
}

output "public_ip" {
  value = data.aws_eip.meetatmensa.public_ip
}