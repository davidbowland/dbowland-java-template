import * as aws from '@pulumi/aws'

import { api } from './apis'
import { createdBy, createdFor } from '@vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigatewayv2/stage/

export const apiStage = new aws.apigatewayv2.Stage('v1', {
  apiId: api.id,
  autoDeploy: true,
  name: 'v1',
  tags: {
    'created-by': createdBy,
    'created-for': createdFor,
  },
})
