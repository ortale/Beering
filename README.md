## Project Overview
The project is an Android application built using Kotlin, Jetpack Compose for the UI, and Hilt for dependency injection. It revolves around displaying a list of beers and detailed information about each beer. The architecture follows the MVI (Model-View-Intent) pattern.

## Project Structure
Presentation Layer: This includes the UI components and ViewModels.

* Screens: Composable functions that represent the screens in the app (BeerDetailScreen, BeerListScreen).
* ViewModel: BeerViewModel which handles the UI-related data and logic.
* Domain Layer: This includes the core business logic.

* Model: Data classes like Beer.
* Use Cases: GetBeersUseCase, GetBeerByIdUseCase, which encapsulate the logic for retrieving data.
* Data Layer: (Not explicitly shown in the provided code but typically would include repositories and data sources).
