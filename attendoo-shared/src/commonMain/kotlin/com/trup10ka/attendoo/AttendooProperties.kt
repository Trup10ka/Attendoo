package com.trup10ka.attendoo

const val CLIENT_HOST_VAL = "localhost:65525"

/* === JSON field names */
const val SUCCESS_JSON_FIELD_NAME = "success"
const val ERROR_JSON_FIELD_NAME = "error"
const val USERNAME_JSON_FIELD = "username"
const val ROLE_JSON_FIELD = "role"

/* === API Endpoints === */
const val API_ROOT_ENDPOINT = "/api"
const val ATTENDANCES_ENDPOINT = "/attendances"
const val USERS_ENDPOINT = "/users"
const val REQUESTS_ENDPOINT = "/requests"
const val ROLES_ENDPOINT = "/roles"
const val AUTH_ENDPOINT = "/auth"

/* === Auth Endpoints === */
const val LOGIN_ENDPOINT = "/login"
const val REGISTER_ENDPOINT = "/register"
const val VERIFY_ENDPOINT = "/verify"
const val FULL_LOGIN_ENDPOINT = "$API_ROOT_ENDPOINT$AUTH_ENDPOINT$LOGIN_ENDPOINT"
const val FULL_REGISTER_ENDPOINT = "$API_ROOT_ENDPOINT$AUTH_ENDPOINT$REGISTER_ENDPOINT"
const val FULL_VERIFY_ENDPOINT = "$API_ROOT_ENDPOINT$AUTH_ENDPOINT$VERIFY_ENDPOINT"

/* === Users Endpoints === */
const val EDIT_USER_ENDPOINT = "/edit-user"
const val GET_ALL_USERS_ENDPOINT = "/all"

/* === Attendances Endpoints === */
const val ALL_ATTENDANCE_ENDPOINT = "/all"
const val USER_ATTENDANCE_ENDPOINT = "/user"

/* === Requests Endpoints === */

/* === Roles Endpoints === */
const val ALL_ROLES_ENDPOINT = "/all"


/* === JWT field names */
const val JWT_USERNAME_FIELD = "attendoo-username"
const val JWT_ROLE_FIELD = "attendoo-role"
const val JWT_DEPARTMENT_FIELD = "attendooDepartment"

/* === Storage keys === */
const val TOKEN_NAME = "ATTENDOO_TOKEN"
const val STATUS_NAME = "ATTENDOO_STATUS"
