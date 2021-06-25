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
$ npm run release
    #=> resources/public/js/compiled/app.js
```
