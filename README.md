# ⚡ PvP FPS Boost Mod for Minecraft 1.21.1 (Fabric)

FPS特化型PvPクライアントMod。全ての最適化をFPS向上に集中させた軽量設計。

---

## 📊 最適化内容 (FPS影響度別)

### 🔴 HIGH インパクト最適化
| 最適化 | 効果 | 仕組み |
|--------|------|--------|
| **パーティクル全廃止** | +20~50 FPS | `ParticleManagerMixin` でパーティクル生成をキャンセル |
| **天気エフェクト廃止** | +10~30 FPS | `WorldRendererMixin` で雨・雪レンダリングをスキップ |
| **VSync強制オフ** | 入力遅延-17ms | ウィンドウ再フォーカス時も維持 |
| **FPS上限解除** | MAX 260 FPS | フレームキャップを削除 |

### 🟡 MEDIUM インパクト最適化
| 最適化 | 効果 | 仕組み |
|--------|------|--------|
| **フォグ(霧)廃止** | +5~20 FPS + 視界UP | `BackgroundRendererMixin` + `FogMixin` |
| **エンティティシャドウ廃止** | +5~15 FPS | `EntityRendererMixin` |
| **雲廃止** | +5~10 FPS | `WorldRendererMixin` |
| **エンティティカリング** | +5~15 FPS | 遠距離の小エンティティをスキップ |
| **バイオームブレンド 0** | +5~10 FPS | 色補間計算を省略 |
| ~~スムースライティングOFF~~ | (有効化済み) | ユーザー設定によりON |

### 🟢 LOW インパクト最適化
| 最適化 | 効果 | 仕組み |
|--------|------|--------|
| **ビネット廃止** | +1~3 FPS | `InGameHudMixin` |
| **アニメーションテクスチャOFF** | +3~8 FPS | 水・溶岩アニメをスキップ |
| **グラフィックモード: Fast** | +5~15 FPS | Vanilla設定を強制適用 |
| **ミップマップ Lv.0** | +2~5 FPS | テクスチャフィルタリング省略 |
| **ケープ廃止** | +1~5 FPS/人 | 物理シミュレーション省略 |
| **周囲サウンドOFF** | スタッター軽減 | サウンドスレッド負荷削減 |
| **定期GCヒント** | スパイク軽減 | 5分ごとにメモリ最適化 |

---

## 🛠️ インストール方法

### 必要なもの
- **Minecraft 1.21.1**
- **Fabric Loader 0.16.5+**
- **Fabric API 0.102.0+1.21.1**
- **Java 21**

### 手順
1. [Fabric Installer](https://fabricmc.net/use/installer/) でFabricをインストール
2. [Fabric API](https://modrinth.com/mod/fabric-api) をダウンロード
3. `pvpfps-1.0.0.jar` を `.minecraft/mods/` に入れる
4. Fabric APIも同じフォルダに入れる
5. 起動！自動的に全最適化が適用される

### おすすめの組み合わせMod
一緒に入れるとさらにFPS UP:
- **Sodium** - レンダリングエンジン置き換え (最重要!)
- **Lithium** - サーバーサイド最適化
- **Iris** - Sodiumと互換のシェーダー管理
- **FerriteCore** - メモリ使用量削減
- **EntityCulling** - より高度なエンティティカリング

---

## ⚙️ 設定ファイル

`.minecraft/config/pvpfps.json` で細かく調整可能:

```json
{
  "disableAllParticles": true,       // パーティクル全廃止 (最大FPS)
  "disableClouds": true,             // 雲廃止
  "disableWeatherRendering": true,   // 天気エフェクト廃止
  "disableFog": true,                // フォグ廃止 (視界UP!)
  "disableEntityShadows": true,      // エンティティシャドウ廃止
  "disableAnimatedTextures": true,   // アニメテクスチャ廃止
  "mipmapLevel": 0,                  // ミップマップ最小
  "entityRenderDistance": 32,        // エンティティ描画距離 (ブロック)
  "enableEntityCulling": true,       // エンティティカリング
  "disableVignette": true,           // ビネット廃止
  "disableAmbientSounds": true,      // 環境音廃止
  "soundChannelCount": 8,            // サウンドチャンネル数削減
  "showFpsCounter": true,            // FPSカウンター表示
  "showAttackCooldown": true         // 攻撃クールダウン表示
}
```

---

## 🔨 ビルド方法

```bash
# リポジトリのクローン or ファイル配置後
./gradlew build

# ビルド成果物は以下に生成される:
# build/libs/pvpfps-1.0.0.jar
```

### 前提条件
- JDK 21
- インターネット接続 (Gradle依存関係ダウンロード用)

---

## 📁 プロジェクト構造

```
pvp-fps-mod/
├── build.gradle
├── gradle.properties
├── settings.gradle
└── src/main/
    ├── java/com/pvpfps/mod/
    │   ├── PvpFpsMod.java              # メインエントリポイント
    │   ├── config/
    │   │   └── FpsConfig.java          # 設定管理
    │   ├── util/
    │   │   └── FpsOptimizer.java       # コア最適化ロジック
    │   └── mixin/
    │       ├── ParticleManagerMixin    # パーティクル廃止 🔴
    │       ├── WorldRendererMixin      # 天気・雲廃止 🔴
    │       ├── BackgroundRendererMixin # フォグ廃止 🟡
    │       ├── EntityRendererMixin     # シャドウ廃止 🟡
    │       ├── EntityMixin             # エンティティカリング 🟡
    │       ├── FogMixin               # ネザーフォグ廃止 🟡
    │       ├── InGameHudMixin         # HUD軽量化 🟢
    │       ├── ClientWorldMixin       # アニメテクスチャ廃止 🟢
    │       ├── MinecraftClientMixin   # VSync強制オフ 🟢
    │       ├── SoundSystemMixin       # サウンド最適化 🟢
    │       ├── GameRendererMixin      # レンダーフック 🟢
    │       ├── ChunkBuilderMixin      # チャンクビルド 🟢
    │       └── AbstractClientPlayerEntityMixin # ケープ廃止 🟢
    └── resources/
        ├── fabric.mod.json
        └── pvpfps.mixins.json
```

---

## ⚠️ 注意事項

- このModは**クライアントサイド**のみで動作します
- サーバーには入れ**ない**でください
- バニラ設定の一部を自動上書きします
- 設定ファイルで各機能のON/OFFが可能です

---

## 🎮 PvP向け推奨設定

```
描画距離: 6~8チャンク
エンティティ描画距離: 32~64ブロック
VSync: OFF (Mod自動設定)
FPS上限: 260 (Mod自動設定)
グラフィック: Fast (Mod自動設定)
```

**良いPvPを！** ⚔️
