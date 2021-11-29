import * as aws from '@pulumi/aws'

import { api } from './apis'
import { apiDomain } from './domain-names'
import { apiStage } from './stages'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigatewayv2/apimapping/

export const v1ApiMapping = new aws.apigatewayv2.ApiMapping('v1-api-mapping', {
  apiId: api.id,
  apiMappingKey: apiStage.name,
  domainName: apiDomain.domainName,
  stage: apiStage.id,
})
