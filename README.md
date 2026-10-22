# Rate CS Staff

**Application URL:** [https://com-harpoonwithaz-cmpt276-a2.onrender.com/](https://com-harpoonwithaz-cmpt276-a2.onrender.com/)

**GitHub Repository:** [https://github.com/harpoonwithaz/com.harpoonwithaz.cmpt276-a2](https://github.com/harpoonwithaz/com.harpoonwithaz.cmpt276-a2)

## How to run locally

### Configure environment variables

Create a `.env` in the root of the project directory to contain all of the database secrets.
Make sure to include the 3 variables needed to run the app locally: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
Set each of the variables to there respective values, replacing whats inside of the `<brackets>` including the brackets.

```.env
DB_URL=jdbc:postgresql://<database url>
DB_USERNAME=<database username>
DB_PASSWORD=<database password>
```

You can view an example here in the [environment variables example file](.env.example).
The `DB_URl` must be `jdbc:postgresql://`, followed by the external database URL after the `@` symbol.

### Inject environment variables

Once you have created a file to store the environment variables, you must inject them into your shell/terminal environment that you use to run the application.
On linux this can be done by doing:

```bash
set -a && source .env && set +a
```

Then you can run the application using that terminal instance using the `mvnw` script.

```bash
./mvnw spring-boot:run
```

Or, use the provided script which injects the environment variables and runs the spring-boot project.

```zsh
chmod 750 run.sh # give the script execute permissions
./run.sh
```

## How to deploy

### PostgreSQL Database

First create a new *Postgres* database on Render (or any hosting platform of your choice).
All you need is access to the: `Username`, `Password`, `Internal/External Database URL`.

### Web Service

Create a new *Web Service* on render and connect it to the github repository.
Make sure the source is set to the github repository and the branch is set to `main`.
Set the application type/code to `Docker` (if render has not done that for you already).
Under environment variables, make sure to add 3 fields: *`DB_URL`*, *`DB_USERNAME`*, *`DB_PASSWORD`*.
For the *`DB_USERNAME`* and *`DB_PASSWORD`*, copy the exact values `Username` and `Password` from the *Postgres* database you made earlier.
For the *DB_URL*, at the beginning include `jdbc:postgresql://`, and after that make sure you you copy the URL **AFTER** the `@` symbol, and paste it end the end.

## Known Issues & Future Work

- In the edit form, if the name is left blank, it gives an error for "Name cannot be blank" and "Name must be between 2 and 100 characters", rather than just one error.
- Email validates on the server side using the `@Email` annotation from `import jakarta.validation.constraints.*;` rather than implementing my own complex email verification.
- The backend returns a model which contains a list of ratings rather than a single rating object.
