package com.trup10ka.attendoo

const val CLIENT_HOST_VAL = "localhost:65525"

/* === JSON field names */
const val SUCCESS_JSON_FIELD_NAME = "success"
const val ERROR_JSON_FIELD_NAME = "error"
const val USERNAME_JSON_FIELD = "username"
const val ROLE_JSON_FIELD = "role"
const val DEPARTMENT_JSON_FIELD = "department"

/* === API Endpoints === */
const val API_ROOT_ENDPOINT = "/api"
const val ATTENDANCES_ENDPOINT = "/attendances"
const val USERS_ENDPOINT = "/users"
const val REQUESTS_ENDPOINT = "/requests"
const val ROLES_ENDPOINT = "/roles"
const val AUTH_ENDPOINT = "/auth"
const val DEPARTMENTS_ENDPOINT = "/departments"

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
const val FULL_ALL_USERS_ENDPOINT = "$API_ROOT_ENDPOINT$USERS_ENDPOINT$GET_ALL_USERS_ENDPOINT"

/* === Attendances Endpoints === */
const val ALL_ATTENDANCE_ENDPOINT = "/all"
const val USER_ATTENDANCE_ENDPOINT = "/user"
const val FULL_ALL_ATTENDANCES_ENDPOINT = "$API_ROOT_ENDPOINT$ATTENDANCES_ENDPOINT$ALL_ATTENDANCE_ENDPOINT"

/* === Requests Endpoints === */
const val GET_ALL_REQUESTS_ENDPOINT = "/all"
const val CREATE_REQUEST_ENDPOINT = "/create"
const val FULL_ALL_REQUESTS_ENDPOINT = "$API_ROOT_ENDPOINT$REQUESTS_ENDPOINT$GET_ALL_REQUESTS_ENDPOINT"

/* === Roles Endpoints === */
const val ALL_ROLES_ENDPOINT = "/all"
const val FULL_ALL_ROLES_ENDPOINT = "$API_ROOT_ENDPOINT$ROLES_ENDPOINT$ALL_ROLES_ENDPOINT"

/* === Departments Endpoints === */
const val ALL_DEPARTMENTS_ENDPOINT = "/all"
const val FULL_ALL_DEPARTMENTS_ENDPOINT = "$API_ROOT_ENDPOINT$DEPARTMENTS_ENDPOINT$ALL_DEPARTMENTS_ENDPOINT"

/* === JWT field names */
const val JWT_USERNAME_FIELD = "attendoo-username"
const val JWT_ROLE_FIELD = "attendoo-role"
const val JWT_DEPARTMENT_FIELD = "attendoo-department"

/* === Storage keys === */
const val TOKEN_NAME = "ATTENDOO_TOKEN"
const val STATUS_NAME = "ATTENDOO_STATUS"
