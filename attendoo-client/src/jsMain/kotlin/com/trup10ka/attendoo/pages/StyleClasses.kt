package com.trup10ka.attendoo.pages

enum class StyleClasses
{
    /* === Global === */
    FORM_FIELD,
    SUBMIT_BUTTON,

    /* === Login ===  */
    LOGIN_PANE,
    LOGIN_FORM,
    LOGIN_PANE_HEADER_TEXT,
    LOGIN_REGISTER_ACTION;
    
    override fun toString() = this.name.lowercase().replace('_', '-')
}
