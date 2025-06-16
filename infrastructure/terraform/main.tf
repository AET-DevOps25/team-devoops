provider "aws" {
  region = var.aws_region
}

# Create a security group
resource "aws_security_group" "app" {
  name        = "app-sg"
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

  vpc_security_group_ids = [aws_security_group.app.id]

  tags = {
    Name = "app-instance"
  }

  # This will output the public IP after creation
  provisioner "local-exec" {
    command = "echo ${self.public_ip} > ec2_public_ip.txt"
  }
}

# Output the public IP
output "public_ip" {
  value = aws_instance.app.public_ip
} 