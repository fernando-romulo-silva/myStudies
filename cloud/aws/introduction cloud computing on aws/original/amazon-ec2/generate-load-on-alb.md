# Command to generate load on the ALB

**_replace with your alb dns name_**
`for i in {1..200}; do curl http://ALB1-1149402601.sa-east-1.elb.amazonaws.com & done; wait`
