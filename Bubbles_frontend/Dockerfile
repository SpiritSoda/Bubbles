FROM node:19.7.0 AS build
WORKDIR /app

COPY package*.json /app/
RUN npm set registry https://registry.npm.taobao.org
RUN npm install
COPY . /app/
RUN npm run build

FROM  nginx
COPY --from=build /app/dist/  /usr/share/nginx/html/

EXPOSE 5173