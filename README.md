# MPのおと

## コンセプト

## 使い方

## 開発

- ClojureScript
- node.js
- shadow-cljs
- re-frame
- garden

```shell-session
$ lein new re-frame mpnote +10x +cider +garden
$ cd mpnote
$ npm install
$ npm run watch
$ vim src/mpnote/views.cljs
    :LspInstallServer
    :CljEval (shadow/repl :app)
```

Open `http://localhost:8280`

## リリース

```shell-session
$ git co master
$ npm run release
    #=> resources/public/js/compiled/app.js
$ git co prod
$ git merge master
$ cp -r resources/public/* docs/
$ vim docs/index.html
    Fix src of app.js
$ git add .
$ git com -m "v1.0"
$ git push github prod
```
