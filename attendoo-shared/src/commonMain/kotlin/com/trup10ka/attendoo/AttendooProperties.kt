package com.trup10ka.attendoo

const val CLIENT_HOST_VAL = "localhost"

const val SUCCESS_JSON_FIELD_NAME = "success"
const val ERROR_JSON_FIELD_NAME = "error"

/* === Auth Endpoints === */
const val LOGIN_ENDPOINT = "/api/auth/login"
const val REGISTER_ENDPOINT = "/api/auth/register"

/* === JWT field names */
const val JWT_USERNAME_FIELD = "attendooUsername"
const val JWT_ROLE_FIELD = "attendooRole"
const val JWT_DEPARTMENT_FIELD = "attendooDepartment"

/* === Storage keys === */
const val TOKEN_NAME = "ATTENDOO_TOKEN"
const val STATUS_NAME = "ATTENDOO_STATUS"
