package service.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import service.demo.Hello;
import service.demo.Hello.Processor;
import service.demo.HelloServiceImpl;

/**
 * 服务器端 将 HelloServiceImpl 作为具体的处理器传递给 Thrift 服务器
 * 
 * @author wuduolin
 * 
 */
public class HelloServiceServer {
	/**
	 * 启动Thrift服务器
	 */
	@SuppressWarnings("rawtypes")
	public void startServer() {

		try {
			// 定义传输的socket，设置服务端口为8088
			TServerSocket serverTransport = new TServerSocket(8088);

			// 设置协议工厂为 TBinaryProtocol.Factory
			Factory proFactory = new TBinaryProtocol.Factory(true, true);

			// 关联处理器与 Hello服务的实现
			@SuppressWarnings("unchecked")
			Hello.Processor processor = new Processor(new HelloServiceImpl());

			// 定义服务端的参数值
			Args args = new Args(serverTransport);
			args.processor(processor);

			args.protocolFactory(proFactory);
			TServer server = new TThreadPoolServer(args);

			// 服务端开启服务s
			server.serve();

		} catch (TTransportException e) {
			System.out.println("========服务端启动失败=========");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("========服务端开始启动=========");
		HelloServiceServer server = new HelloServiceServer();
		server.startServer();
	}
}
