FROM node:22-alpine

ENV NODE_ENV=production PORT=3349 HOST=0.0.0.0
WORKDIR /app

COPY --chown=node:node server.js index.html 404.html style.css ./
COPY --chown=node:node js/app.js js/calc-core.js js/erro.js ./js/

USER node
EXPOSE 3349
CMD ["node", "server.js"]
