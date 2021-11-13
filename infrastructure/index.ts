// Import Pulumi configuration
import './config'

// Import modules to create resources
import '@api-gateway'
import '@cloudwatch'
import '@codedeploy'
import '@ec2'
import '@ecr'
import { ecsService } from '@ecs'
import '@iam'
import { loadBalancer } from '@lb'

// Output

export const loadBalancerDnsName = loadBalancer.loadBalancer.dnsName
export const taskDefinitionArn = ecsService.taskDefinition.taskDefinition.arn
