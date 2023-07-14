# ログインサービス
 - 一度エンジニアから別業種への経験を経て、再びエンジニアとして活躍していきたいと思い、日々言語や資格の取得に励んでおります。
   
   その為にもいくつかアプリケーションの作成を行い、見える形として成果物を残していきます。

   ポートフォリオを作成していない期間は、直近で基本情報技術者試験の資格を取得しました。

   現在はアプリケーションの作成を行いながら営業中です。

   今回作成したアプリケーションはサービス化しており、以下のURLより確認が可能です。

   ※管理者とユーザーで権限が分かれており、管理者のみアカウント作成済みでございます。
   
   管理者のIDとパスワードは、ID:admin, PASSWORD:adminでログインが確認可能です。
    
   [ログインサービス](http://13.231.105.240:8888/login).

## 1.アプリケーションについて
 - ログインサービスは、アカウントを作成しログインを行わないとコンテンツが閲覧できないSNSライクなサービスです。
   - 機能の紹介
     - ログイン(登録済みのIDとパスワードに紐づくアカウント情報でログインが可能)
     - アカウント情報新規登録(ユーザーがアカウント情報を登録する)
     - アカウント情報詳細(ユーザーがアカウント情報に紐づくアカウントの詳細を閲覧可能)
     - アカウント情報更新(ユーザーがアカウント情報を更新する)
     - アカウント情報削除(ユーザーがアカウント情報を論理削除する)
     - マイページ(ユーザーアイコンを押下でユーザーアカウントの情報が閲覧可能)
     - お気に入り画面(お気に入りした投稿情報が確認可能)
     - 投稿情報一覧(登録済みの全ての登録の情報が確認可能)
     - お気に入り機能(投稿情報一覧からお気に入りする情報をマイページのお気に入りリストへ登録または解除が可能)
     - 投稿情報詳細(IDに紐づく投稿の情報が閲覧可能)
     - 投稿情報登録(管理者が投稿情報を新たに登録する)
     - 投稿情報更新(管理者が編集ボタンから登録済みの投稿情報を更新する)
     - 投稿情報削除(管理者が投稿情報一覧から削除したい投稿情報を論理削除する)
       
   - 構築環境
     - 各バージョン
       - Java ver17
       - Spring Boot ver3.1.0
       - Spring Security ver3.1.1
       - mybatis-spring-boot-starter ver3.0.2
       - Model Mapper ver 3.1.0
       - jquery ver 3.6.1
       - bootstrap ver5.2.2
       - webjars-locator ver 0.46
       - thymeleaf-layout-dialect ver 3.1.1
       - EC2
       - Mysql ver8.0.32(AWS)
       - Eclipse ver4.27.0(2023-03)
       - Spring Framework ver6.0.9
       - Git
   - ER図(データベース設計書)
     - ![ER Image 1](/loginAplication.png)
   - AWS構成図
     - ![AWS Image 2](/AWS_login.png)
## 2.課題と成長したことについて
 - 成長したこと
   - 今回は、ログイン機能付きのアプリケーションを作成してみたいということで挑戦してみました。

     ログイン機能を作る上でSpring Securityが必要であり、実際に作成を行ってみると権限や認証や認可の部分で最初つまづいたりしましたが
     
     前回、Springを触っていた経験が活かされログイン機能を作ることが出来ました。
     
     また、ログイン後のコンテンツでは、ログインの際に管理者とユーザーで権限を分けており、それぞれのロールで行えることが変わります。
     
     セキュリティー面を意識してCSFR対策やパスワードを登録する際にハッシュ化してから登録を行っております。
     
     コンテンツの作成で一番力を入れたのはお気に入り登録機能です。
     
     アプリケーションがSNSライクな機能もあり、ユーザーがお気に入りした投稿内容を再度確認したいときに自分のマイページから確認が行えるようにしたかった為
     
     試行錯誤を重ねた結果、ユーザーがお気に入りしたい投稿内容のみ自分のページで確認を行えることが可能になりました。
     
     今回のアプリケションでは、以前作成したアプリケーションで実現できなかったDB関係も自分なりに満足いく形で関連付けて作成を行えたことも成長したと感じます。
   　　
- 課題
  - 今回の課題点は、複雑な機能を作成するにあたりコンテンツの全体がみえてなく、その為作成に時間がかかったことだと感じました。
   
    その理由は作成中に所々、開発環境やAWS周りでエラーが発生し開発が遅延等しましたがそれを考慮できてなかったと考えております。
    
    次作成するアプリケーションでは、開発時のエラーや事故を含めた上で開発の工数を考慮して開発やテストを行っていこうと思います。

   
      
   
