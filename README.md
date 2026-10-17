# Rate CS Staff

## How to run locally

### Configure environment variables

Create a `.env` in the root of the project directory to contain all of the sensitive information.
Make sure to include the 3 variables needed to run the app locally: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
Set each of the variables to there respective values, replacing whats inside of the `<brackets>` including the brackets.

```.env
DB_URL=<database url>
DB_USERNAME=<database username>
DB_PASSWORD=<database password>
```

You can view an example here in the [environment variables example file](.env.example)

### Inject environment variables

Once you have created a file to store the environment variables, you must inject them into your shell/terminal environment that you
use to run the application. On linux this can be done by doing:

```bash
set -a && source .env && set +a
```

Then you can run the application using that terminal instance using the `mvnw` script.

```bash
./mvnw spring-boot:run
```

Or, use the provided script which injects the environment variables and runs the spring-boot project.

```zsh
chmod 750 run.sh
./run.sh
```
