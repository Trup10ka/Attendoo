# Attendoo

Attendoo is a web SPA for organizations and their members to manage their attendances.

The predominant goal for this app is to provide information about whether a member is on home office, ill, on vacation, or present in the office. This information is important for the organization to plan their resources and to know who is available for a meeting or a task.

App and server are written 100% in [Kotlin](https://kotlinlang.org/), web part is developed using [Kotlin/JS](https://kotlinlang.org/docs/js-overview.html).

# Features
- [] Users can request a home office
- [] Users can request a vacation
- [] Users can request a sick leave
- [] Organization group admins can approve or reject requests
- [] Organization group admins can create accounts for the members of the organization
- [] Organization group admins can see the status of all members from the organization
- [] Users get notifications by email when their request is approved or rejected
- [] Users get notifications by email when any changes to their timetables are made

# Deployment process
- Firstly, the app and the database are set up
- After the app is configured and running, the organization admin **must** create his account and also create an organization group.
- The organization group admin is then able to create accounts for the members of the organization.
  - The User for whom the account was created will receive an email with a link to set up his password.
  - The User is also prompted to check his account information and update it if necessary. Any changes that are made by the user must be approved by the organization group admin.
- After the user has set up his password, he can log in and set his status.
