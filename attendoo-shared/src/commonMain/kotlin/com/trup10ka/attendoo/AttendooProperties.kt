package com.trup10ka.attendoo

const val CLIENT_HOST_VAL = "localhost"

/* === JSON field names */
const val SUCCESS_JSON_FIELD_NAME = "success"
const val ERROR_JSON_FIELD_NAME = "error"
const val USERNAME_JSON_FIELD = "username"
const val ROLE_JSON_FIELD = "role"

/* === Auth Endpoints === */
const val LOGIN_ENDPOINT = "/api/auth/login"
const val REGISTER_ENDPOINT = "/api/auth/register"
const val VERIFY_ENDPOINT = "/api/auth/verify"

/* === JWT field names */
const val JWT_USERNAME_FIELD = "attendoo-username"
const val JWT_ROLE_FIELD = "attendoo-role"
const val JWT_DEPARTMENT_FIELD = "attendooDepartment"

/* === Storage keys === */
const val TOKEN_NAME = "ATTENDOO_TOKEN"
const val STATUS_NAME = "ATTENDOO_STATUS"
