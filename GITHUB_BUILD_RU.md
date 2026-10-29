# Compact Rotation Speed Controller — сборка через GitHub

Проект настроен на Minecraft 1.21.1, Java 21, NeoForge и Create 6.x.

## Как получить JAR на GitHub (Windows)

1. Войди на https://github.com
2. Нажми `+` → `New repository`.
3. Назови репозиторий `compact-speed-controller`. Для простоты можно выбрать `Public` или `Private`; затем нажми `Create repository`.
4. На странице репозитория нажми `Add file` → `Upload files`.
5. Открой папку `compact-speed-controller` из этого архива на компьютере и перетащи **содержимое папки** в окно загрузки GitHub. Важно загрузить папку `.github` вместе с остальными файлами. Если Windows не показывает `.github`, включи показ скрытых элементов или используй GitHub веб-интерфейс для загрузки файлов.
6. Нажми `Commit changes`.
7. Открой вкладку `Actions` в репозитории и выбери workflow `Build Minecraft mod`.
8. Если сборка не запустилась автоматически, нажми `Run workflow` → `Run workflow`.
9. Открой завершившийся запуск (зелёная галочка), прокрути вниз до `Artifacts` и скачай `compact-speed-controller-jar`.
10. Распакуй скачанный ZIP. Внутри должен быть `.jar`; положи его в папку `mods` Minecraft вместе с совместимыми NeoForge и Create.

## Важно

- Эта настройка автоматизирует сборку, но не гарантирует, что исходники компилируются без ошибок. Если GitHub Actions покажет ошибку, открой запуск, скопируй последние строки лога и пришли их сюда.
- Для Minecraft 1.21.1 нужны соответствующие NeoForge и версия Create для NeoForge. Не устанавливай этот мод в Forge/Fabric-профиль.
- Не загружай в репозиторий пароли, токены или другие секреты.
