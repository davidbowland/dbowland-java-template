import * as aws from '@pulumi/aws'

import { codeDeployPolicy, ecsTaskExecutionPolicy } from './policies'
import { codeDeployServiceRole, ecsTaskExecutionRole } from './roles'

// https://www.pulumi.com/docs/reference/pkg/aws/iam/rolepolicyattachment/

export const codeDeployCustomPolicy = new aws.iam.RolePolicyAttachment('codedeploy-service-codeDeployPolicy', {
  role: codeDeployServiceRole,
  policyArn: codeDeployPolicy.arn,
})

export const codeDeployServiceRoleAttachment = new aws.iam.RolePolicyAttachment(
  'codedeploy-service-AWSCodeDeployRoleForECS',
  {
    role: codeDeployServiceRole,
    policyArn: aws.iam.ManagedPolicy.AWSCodeDeployRoleForECS,
  }
)

export const ecsTaskExectionCloudWatchFullAccess = new aws.iam.RolePolicyAttachment(
  'ecs-task-execution-CloudWatchFullAccess',
  {
    role: ecsTaskExecutionRole,
    policyArn: aws.iam.ManagedPolicy.CloudWatchFullAccess,
  }
)

export const ecsTaskExecutionCustomPolicy = new aws.iam.RolePolicyAttachment(
  'ecs-task-execution-ecsTaskExecutionPolicy',
  {
    role: ecsTaskExecutionRole,
    policyArn: ecsTaskExecutionPolicy.arn,
  }
)

export const ecsTaskExectionRolePolicy = new aws.iam.RolePolicyAttachment(
  'ecs-task-execution-AmazonECSTaskExecutionRolePolicy',
  {
    role: ecsTaskExecutionRole,
    policyArn: aws.iam.ManagedPolicy.AmazonECSTaskExecutionRolePolicy,
  }
)
