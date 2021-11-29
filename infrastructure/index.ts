// Import Pulumi configuration
import './config'

// Import modules to create resources
import '@api-gateway'
import '@cloudwatch'
import '@codedeploy'
import '@ec2'
import '@ecr'
import '@ecs'
import '@iam'
import '@lb'
import '@route53'

// Output

import { ecsService } from '@ecs'
import { loadBalancer } from '@lb'

export const loadBalancerDnsName = loadBalancer.loadBalancer.dnsName
export const taskDefinitionArn = ecsService.taskDefinition.taskDefinition.arn
