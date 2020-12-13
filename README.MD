# Widgets Application
Web service to organize widgets

## Starting the application

1) Clone this repository.

```sh
git clone git@github.com:alemasseroli/widgets.git
```

2) Enter project's directory.

```sh
cd widgets
```

3) Start web application running jar file.

```sh
java -jar widgets.jar
```

## Usage

- Create Widget:

```sh
curl -X POST http://localhost:8080/widgets \
-H 'Content-Type: application/json' \
-d '{"x": 50, "y":50, "z":1, "width":100, "height":100}'
```

- Get widget:

```sh
curl -XGET http://localhost:8080/widgets/${id}
```

- Get all widgets:

```sh
curl -XGET http://localhost:8080/widgets
```

- Search widgets filtering by position:

```sh
curl -XPOST http://localhost:8080/widgets/search \
-H 'Content-Type: application/json' \
-d '{"x1":0, "y1":0, "x2":100, "y2":150}'
```

- Delete widget: 

```sh
curl -XDELETE http://localhost:8080/widgets/${id}
```

- Change widget data:
```sh
curl -XPUT http://localhost:8080/widgets/${id} \
-H 'Content-Type: application/json' \
-d '{"x": 100, "y": 100}'
```
