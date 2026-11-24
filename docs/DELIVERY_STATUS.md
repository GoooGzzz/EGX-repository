# Delivery status

## Included
- Responsive market overview, screener, local watchlist, hypothetical portfolio calculator and research/disclosure workspace.
- Android Kotlin + Jetpack Compose starter navigation.
- Optional Cloudflare Pages deployment workflow.

## Data integrity
Every displayed quote, index, turnover and portfolio figure is illustrative. Do not present it as live EGX data. Production requires an authorized source, redistribution rights, source timestamps, delayed/real-time labels, stale-data handling and corporate-action validation.

## Production gates
1. Confirm the Egyptian regulatory classification and intended service with qualified counsel and the FRA.
2. Obtain EGX market data and content redistribution rights.
3. Implement backend, API, database, authentication, tenant controls and audit logs.
4. Complete security/privacy assessment, load testing, UAT and Android release signing.

## Deployment
The GitHub Actions workflow deploys `prototype/` to a Cloudflare Pages project named `egx-intelligence`. It requires repository secrets `CLOUDFLARE_API_TOKEN` and `CLOUDFLARE_ACCOUNT_ID`, plus that Pages project to exist.