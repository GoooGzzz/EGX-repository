export default {
  async fetch(request, env) {
    const url = new URL(request.url);
    if (url.pathname === "/health") {
      return Response.json({
        status: "ok",
        service: "EGX Intelligence",
        environment: "demo",
        marketData: "illustrative-only"
      });
    }
    return env.ASSETS.fetch(request);
  }
};
