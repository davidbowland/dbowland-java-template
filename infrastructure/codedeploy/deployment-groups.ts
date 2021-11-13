import * as aws from '@pulumi/aws'

import { codeDeployServiceRole } from '@iam'
import { applicationListener } from '@lb'
import { appName, createdBy, createdFor } from '@vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/codedeploy/deploymentgroup/

export const deployGroup = new aws.codedeploy.DeploymentGroup('deploy-group', {
  appName,
  autoRollbackConfiguration: {
    enabled: true,
    events: ['DEPLOYMENT_FAILURE'],
  },
  blueGreenDeploymentConfig: {
    deploymentReadyOption: {
      actionOnTimeout: 'CONTINUE_DEPLOYMENT',
    },
    terminateBlueInstancesOnDeploymentSuccess: {
      action: 'TERMINATE',
      terminationWaitTimeInMinutes: 1,
    },
  },
  deploymentConfigName: 'CodeDeployDefault.ECSAllAtOnce',
  deploymentGroupName: appName,
  deploymentStyle: {
    deploymentOption: 'WITH_TRAFFIC_CONTROL',
    deploymentType: 'BLUE_GREEN',
  },
  ecsService: {
    clusterName: appName,
    serviceName: `${appName}-service`,
  },
  loadBalancerInfo: {
    targetGroupPairInfo: {
      prodTrafficRoute: {
        listenerArns: [applicationListener.listener.arn],
      },
      targetGroups: [
        {
          name: `${appName}-1`,
        },
        {
          name: `${appName}-2`,
        },
      ],
    },
  },
  serviceRoleArn: codeDeployServiceRole.arn,
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})
