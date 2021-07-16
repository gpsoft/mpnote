# ピアノ教室のおと(mpnote)

## 背景

[YouTubeチャンネル「むつこピアノ教室」](https://www.youtube.com/channel/UC95wHIr4NKiBcthI8l4klBQ)では、「楽譜を読まずに弾ける!」と題した、いろんな曲のレッスン動画が公開されている。譜読みの苦労から解放され、少しずつ弾けるようになるのが楽しい。

しかし、楽譜を読まずに弾けるようになったとしても、それを忘れないようにするのは、なかなか大変だ(毎日弾かんといけん)。少しブランクが空くと忘れてしまうし、忘れた箇所を動画から探し出すのも時間がかかる。

そんなわけで、レッスンの内容を記録して表示するサイトを作りたい。とは言え、記録するプログラムを書くのは難しそうなので、とりあえず表示だけ。


## コンセプト

重視するのは以下の点。

- 打鍵するキーが分かる
- 指番号が分かる
- ペダルのタイミングが分かる

逆に、以下の点は妥協する。

- 音の長さが分かる
- 音の強さが分かる

## 使う

パソコンやiPadなどのブラウザで、[デモサイト(https://gpsoft.github.io/mpnote/)](https://gpsoft.github.io/mpnote/)を開く。

- ト音記号アイコンをタッチして、レッスンノートを選ぶ
- スピーカアイコンをタッチすると、音も鳴らせる(ブラウザのサポート状況やOSに依存)
- レッスンノートの書き方は、そのうち公開するかなぁ…

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
$ git co prod
$ ./release.sh
$ git com -m "v1.0"
$ git push github prod
$ git co master
```
