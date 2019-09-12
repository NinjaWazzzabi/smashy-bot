import commands.minecraft.McServerPinger
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

const val IP = "81.231.19.201"

fun main() {
    val serverPinger = McServerPinger(IP)
    val onlineStatus : Subject<Boolean> = PublishSubject.create()

    Thread.sleep(500)

    print(serverPinger.isOnline)
    onlineStatus.map { serverPinger.isOnline }.subscribe { println("The server is: $it")}
}