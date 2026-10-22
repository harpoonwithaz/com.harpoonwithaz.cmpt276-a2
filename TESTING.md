# Testing suite

## Validation Tests

Uses the Bean Validation `Validator` to test:

- All valid fields produce no errors (`whenAllFieldsValid_thenNoViolations()`)
- Invalid email is rejected (`whenEmailInvalid_thenValidationFails()`)
- Score stays within range (`whenScoreOutOfRange_thenValidationFails()`)
- Blank name is rejected (`whenNameIsBlank_thenValidationFails()`)

by asserting that the output matches the expected outcome.

## Controllers Tests

`StaffRatingControllerTest` uses a Spring MVC test slice with a mocked repository to verify:

- `GET /ratings` returns HTTP 200, renders the `index` view, and exposes the `ratings` model attribute
- `GET /ratings/{id}` renders the detail page for an existing rating
- `GET /ratings/{id}` returns the 404 error page when the rating does not exist
- `GET /ratings/{id}/edit` renders the edit form for an existing rating
- `GET /ratings/{id}/edit` returns the 404 error page when the rating does not exist
- Valid `POST /ratings` requests save the rating and redirect to `/ratings`
- Invalid `POST /ratings` requests return the create form with validation errors and do not save
- Valid `POST /ratings/{id}/edit` updates the existing rating and redirects to `/ratings`
- Invalid `POST /ratings/{id}/edit` returns the edit form with field errors, preserves the submitted form model, and does not access or save a repository entry
- `POST /ratings/{id}/edit` returns the 404 error page when the rating does not exist
- `POST /ratings/{id}/delete` deletes an existing rating and redirects to `/ratings`
- `POST /ratings/{id}/delete` returns the 404 error page and does not delete when the rating does not exist

Run the controller tests with:

```bash
./mvnw -Dtest=StaffRatingControllerTest test
```

## Persistence Tests

`StaffRatingRepositoryTest` uses an embedded H2 database to verify:

- A saved rating can be retrieved with its persisted values
- Deleting a rating removes it from the repository

Run the persistence tests with:

```bash
./mvnw -Dtest=StaffRatingRepositoryTest test
```

Run the validation tests with:

```bash
./mvnw -Dtest=StaffRatingValidationTest test
```

Run all tests with:

```bash
./mvnw test
```
