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
- Valid `POST /ratings` requests save the rating and redirect to `/ratings`
- Invalid `POST /ratings` requests return the create form with validation errors and do not save

## Persistence Tests

`StaffRatingRepositoryTest` uses an embedded H2 database to verify:

- A saved rating can be retrieved with its persisted values
- Deleting a rating removes it from the repository
