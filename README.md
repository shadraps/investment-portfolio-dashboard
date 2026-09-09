# Investment Portfolio Dashboard

![Java](https://img.shields.io/badge/Java-21-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green)
![React](https://img.shields.io/badge/React-18-blue)
![TypeScript](https://img.shields.io/badge/TypeScript-5-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Supabase](https://img.shields.io/badge/Supabase-Postgres-green)

This is a project I did (and still come back to) to learn Spring Boot and other parts of the Spring Framework, React (with Vite and Axios), Supabase, as well as get experience developing a working full-stack project.

## Features

- Add stock holdings with ticker, shares, and cost basis
- Live price fetching via Alpha Vantage API
- Automatic P&L calculations (value, gain/loss, percent return)
- Portfolio summary cards
- Full CRUD via Spring Boot + Supabase PostgreSQL

## Architecture

```

                                              +----------------------+
                                              |      React UI        |
                                              |  (Vite + Axios)      |
                                              +----------+-----------+
                                                         |
                                                         | REST API Calls
                                                         v
                      +---------------------------------------------------------------+
                      |                    Spring Boot Backend                        |
                      |   Controllers --> Services --> Repositories --> JPA Entities  |
                      +---------------------------------------------------------------+
                                     |                            |
                                     | JDBC                       | HTTP Client
                                     v                            v
                            +----------------+        +---------------------------+
                            |  Supabase      |        | Alpha Vantage API         |
                            | PostgreSQL     |        | (Live market data)        |
                            +----------------+        +---------------------------+
```

## API Endpoints

POST   /api/holdings
GET    /api/holdings
DELETE /api/holdings/{id}
GET    /api/stock/{symbol}
GET    /api/portfolio/summary
