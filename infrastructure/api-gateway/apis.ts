import * as aws from '@pulumi/aws'

import { appName, createdBy, createdFor } from '@vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigatewayv2/api/

export const api = new aws.apigatewayv2.Api('api', {
  name: `${appName}-api`,
  protocolType: 'HTTP',
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})
