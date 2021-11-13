import * as aws from '@pulumi/aws'

import { api } from './apis'
import { appName, cognitoAppClientId, cognitoUserPoolEndpoint } from '@vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigatewayv2/authorizer/

export const cognitoAuthorizer = new aws.apigatewayv2.Authorizer('authorizer', {
  apiId: api.id,
  authorizerType: 'JWT',
  name: `${appName}-authorizer`,
  identitySources: ['$request.header.Authorization'],
  jwtConfiguration: {
    audiences: [cognitoAppClientId],
    issuer: cognitoUserPoolEndpoint,
  },
})
