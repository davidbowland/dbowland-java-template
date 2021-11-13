import * as aws from '@pulumi/aws'
import * as pulumi from '@pulumi/pulumi'

import { api } from './apis'
import { cognitoAuthorizer } from './authorizers'
import { integration } from './integrations'
import { resourceById, resourcePlain } from '@vars'

// https://www.pulumi.com/registry/packages/aws/api-docs/apigatewayv2/route/

const cognitoAuthorization = {
  authorizationType: 'JWT',
  authorizerId: cognitoAuthorizer.id,
}

export const deleteId = new aws.apigatewayv2.Route('delete-id', {
  apiId: api.id,
  ...cognitoAuthorization,
  routeKey: `DELETE ${resourceById}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const getId = new aws.apigatewayv2.Route('get-id', {
  apiId: api.id,
  routeKey: `GET ${resourceById}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const getPlain = new aws.apigatewayv2.Route('get-plain', {
  apiId: api.id,
  routeKey: `GET ${resourcePlain}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const patchId = new aws.apigatewayv2.Route('patch-id', {
  apiId: api.id,
  ...cognitoAuthorization,
  routeKey: `PATCH ${resourceById}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const postPlain = new aws.apigatewayv2.Route('post-plain', {
  apiId: api.id,
  ...cognitoAuthorization,
  routeKey: `POST ${resourcePlain}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})

export const putId = new aws.apigatewayv2.Route('put-id', {
  apiId: api.id,
  ...cognitoAuthorization,
  routeKey: `PUT ${resourceById}`,
  target: pulumi.interpolate`integrations/${integration.id}`,
})
