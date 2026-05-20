# L1J-TW 182 Server

天堂 (Lineage) 私服模擬器原始碼 — 基於 L1J 框架 1.82 版。

## 📋 專案概述

本專案為天堂 1 (Lineage 1) 伺服器端模擬器原始碼，使用 Java 開發，基於 Apache MINA 網路框架處理客戶端通訊。支援台灣版客戶端（BIG5 編碼）。

### 主要特性

- 四大職業：王族 (Royal)、騎士 (Knight)、精靈 (Elf)、法師 (Wizard)
- 完整 NPC 對話與任務系統
- 怪物生成與 AI 行為
- 物品掉落與強化系統
- 血盟 (Clan) 與城堡戰系統
- 組隊系統與經驗分配
- 寵物系統
- 史萊姆競技場
- 加速檢測 (Anti-SpeedHack)
- GM 管理指令集
- 月卡 / 點卡計費系統（可選）

## 📁 專案結構

```
182原始碼/
├── src/                    # Java 原始碼
│   ├── net/
│   │   ├── Server.java           # 伺服器主入口
│   │   ├── Config.java           # 伺服器設定
│   │   ├── LineageClient.java    # 客戶端連線管理
│   │   ├── Opcodes.java          # 封包操作碼定義
│   │   ├── database/             # 資料庫存取層
│   │   ├── network/              # 封包處理 (C_ 客戶端 / S_ 伺服端)
│   │   ├── world/                # 遊戲世界邏輯
│   │   │   ├── instance/         # 遊戲實體 (PC, NPC, Monster, Item)
│   │   │   ├── function/         # 系統功能 (GM, 交易, 血盟, 組隊)
│   │   │   ├── kingdom/          # 城堡系統
│   │   │   ├── npc/              # NPC 腳本
│   │   │   ├── monster/          # 怪物腳本
│   │   │   ├── ai/               # AI 行為
│   │   │   └── time/             # 定時器
│   │   ├── mina/                 # MINA 網路層
│   │   ├── check/                # 封包驗證
│   │   ├── online/               # 在線狀態輸出
│   │   └── util/                 # 工具類
│   └── log4j.properties          # 日誌設定
├── config/                 # 伺服器設定檔
│   ├── config.properties         # 主設定 (資料庫、倍率、端口等)
│   ├── login.properties          # 登入驗證設定
│   ├── html.properties           # HTML 在線輸出設定
│   ├── monster.properties        # 怪物設定
│   ├── char.properties           # 角色設定
│   ├── test.properties           # 測試設定
│   └── c3p0-config.xml           # 資料庫連線池設定
├── db/                     # 資料庫結構與資料
│   ├── l1jdb.sql                 # 完整資料庫匯入檔
│   └── lineage/                  # 分表 SQL 檔案
├── maps/                   # 地圖資料
├── lib/                    # 相依函式庫
├── data/                   # 遊戲資料檔
├── log/                    # 運行日誌
├── client/                 # 客戶端相關資料
├── bin/                    # 編譯輸出
├── l1jserver.exe           # 伺服器啟動程式
└── start.bat               # 啟動腳本
```

## 🔧 環境需求

| 項目 | 版本 |
|---|---|
| Java (JDK) | 1.7+ |
| MySQL | 5.x |
| 作業系統 | Windows |

### 相依函式庫

| 函式庫 | 版本 | 用途 |
|---|---|---|
| Apache MINA | 2.0.9 | 網路通訊框架 |
| MySQL Connector/J | 5.1.15 | 資料庫連線 |
| c3p0 | 0.9.1.2 | 資料庫連線池 |
| SLF4J + Log4j | 1.7.5 / 1.2.17 | 日誌系統 |
| Commons Lang | 3.4 | 工具類 |

## 🚀 安裝與部署

### 1. 建立資料庫

```sql
CREATE DATABASE 182 DEFAULT CHARACTER SET utf8;
USE 182;
SOURCE db/l1jdb.sql;
```

### 2. 修改設定

編輯 `config/config.properties`：

```properties
# 資料庫連線設定
URL = jdbc:mysql://127.0.0.1/182?useUnicode=true&zeroDateTimeBehavior=round&characterEncoding=utf8
Login = root
Password = <your_password>

# 遊戲倍率設定
RateXp = 10          # 經驗倍率
RateDropAdena = 1    # 金幣掉落率
RateDropItems = 1    # 物品掉落率
EnchantChance = 1    # 強化機率

# 伺服器端口
GameserverPort = 2000

# 等級上限
LEVEL_MAX = 90
```

### 3. 編譯原始碼

使用 Eclipse 或命令列編譯：

```bash
javac -cp "lib/*" -d bin -encoding UTF-8 src/net/Server.java
```

### 4. 啟動伺服器

```bash
start.bat
```

或手動執行：

```bash
java -cp "bin;lib/*;src" net.Server
```

## 🎮 GM 指令

以下指令需 GM 帳號（資料庫 `account` 表 `level > 0`）：

| 指令 | 說明 | 用法 |
|---|---|---|
| `.item` | 生成物品 | `.item 物品ID 數量 強化 祝福` |
| `.move` | 傳送座標 | `.move X Y 地圖ID` |
| `.topc` | 傳送到玩家 | `.topc 玩家名` |
| `.call` | 召喚玩家 | `.call 玩家名` |
| `.level` | 設定等級 | `.level 等級` |
| `.buff` | 加狀態 | `.buff [玩家名/*]` |
| `.ban` | 封鎖玩家 | `.ban 玩家名` |
| `.unban` | 解封 IP | `.unban IP` |
| `.loc` | 顯示座標 | `.loc` |
| `.shutdown` | 關閉伺服器 | `.shutdown 秒數` |
| `.reload` | 重載設定 | `.reload` |
| `.monster` | 召喚怪物 | `.monster 怪物ID 數量` |
| `.npc` | 生成 NPC | `.npc NpcID` |
| `.skill` | 全技能 | `.skill` |
| `.bao` | 掉寶查詢 | `.bao 怪物名/物品名` |
| `.search` | 搜索物品 | `.search 物品名` |
| `.gm` | 解除 GM | `.gm` |
| `.version` | 更新日誌 | `.version` |

## ⚠️ 安全注意事項

> **本原始碼已經過安全審計，未發現惡意後門程式。** 以下為發現的安全疏忽，使用前建議修正：

1. **外部 HTTP 連線**：`MonDropTimer.java` 中存在連線至外部網址 (`blackteaya.myweb.hinet.net`) 的程式碼，建議移除。
2. **SQL 注入風險**：部分 SQL 查詢使用字串拼接，建議改用 `PreparedStatement` 參數化查詢。
3. **明文密碼**：`config.properties` 包含資料庫密碼，**請勿將生產環境密碼推送至公開倉庫**。

## 📄 授權

本專案為天堂 1 私服模擬器原始碼，僅供學習與研究用途。
