import * as aws from '@pulumi/aws'

import { api } from './apis'
import { applicationListener } from '@lb'
import { vpcLink } from './vpc-links'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigatewayv2/integration/

export const integration = new aws.apigatewayv2.Integration('integration', {
  apiId: api.id,
  connectionId: vpcLink.id,
  connectionType: 'VPC_LINK',
  integrationMethod: 'ANY',
  integrationType: 'HTTP_PROXY',
  integrationUri: applicationListener.listener.arn,
  timeoutMilliseconds: 5000,
})
