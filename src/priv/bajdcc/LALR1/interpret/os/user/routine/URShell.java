package priv.bajdcc.LALR1.interpret.os.user.routine;

import priv.bajdcc.LALR1.interpret.os.IOSCodePage;

/**
 * 【用户态】脚本解释器
 *
 * @author bajdcc
 */
public class URShell implements IOSCodePage {
	@Override
	public String getName() {
		return "/usr/p/sh";
	}

	@Override
	public String getCode() {
		return "import \"sys.base\";\n" +
				"import \"sys.list\";\n" +
				"import \"sys.string\";\n" +
				"import \"sys.proc\";\n" +
				"\n" +
				"// PRINT WELCOME\n" +
				"var welcome = func [\"WELCOME\"] ~() {\n" +
				"    call g_println();\n" +
				"    call g_printn(\" ________  ________        ___  ________  ________  ________     \");\n" +
				"    call g_printn(\"|\\\\   __  \\\\|\\\\   __  \\\\      |\\\\  \\\\|\\\\   ___ \\\\|\\\\   ____\\\\|\\\\   ____\\\\    \");\n" +
				"    call g_printn(\"\\\\ \\\\  \\\\|\\\\ /\\\\ \\\\  \\\\|\\\\  \\\\     \\\\ \\\\  \\\\ \\\\  \\\\_|\\\\ \\\\ \\\\  \\\\___|\\\\ \\\\  \\\\___|    \");\n" +
				"    call g_printn(\" \\\\ \\\\   __  \\\\ \\\\   __  \\\\  __ \\\\ \\\\  \\\\ \\\\  \\\\ \\\\\\\\ \\\\ \\\\  \\\\    \\\\ \\\\  \\\\       \");\n" +
				"    call g_printn(\"  \\\\ \\\\  \\\\|\\\\  \\\\ \\\\  \\\\ \\\\  \\\\|\\\\  \\\\\\\\_\\\\  \\\\ \\\\  \\\\_\\\\\\\\ \\\\ \\\\  \\\\____\\\\ \\\\  \\\\____  \");\n" +
				"    call g_printn(\"   \\\\ \\\\_______\\\\ \\\\__\\\\ \\\\__\\\\ \\\\________\\\\ \\\\_______\\\\ \\\\_______\\\\ \\\\_______\\\\\");\n" +
				"    call g_printn(\"    \\\\|_______|\\\\|__|\\\\|__|\\\\|________|\\\\|_______|\\\\|_______|\\\\|_______|\");\n" +
				"    call g_println();\n" +
				"};\n" +
				"call g_join_process(call g_create_user_process(welcome));\n" +
				"\n" +
				"var parse_cmd_1 = func [\"PARSE\"] ~(arg) {\n" +
				"    var pid = call g_get_pid();\n" +
				"    var cmd = call g_map_get(arg, \"args\");\n" +
				"    var parent = call g_map_get(arg, \"parent\");\n" +
				"    var exe = call g_array_get(cmd, 0);\n" +
				"    let exe = call g_string_trim(exe);\n" +
				"    var args = call g_string_split(exe, \" \");\n" +
				"    var exec = call g_array_get(args, 0);\n" +
				"    call g_array_remove(args, 0);\n" +
				"    var share = {};\n" +
				"    call g_map_put(share, \"args\", args);\n" +
				"    call g_map_put(share, \"parent\", parent);\n" +
				"    call g_start_share(\"PID#\" + pid, share);\n" +
				"    var path = \"/usr/p/\" + exec;\n" +
				"    var child = call g_load_user_x(path);\n" +
				"    if (child+1 == 0) {\n" +
				"        call g_printn(\"Cannot execute '\"+path+\"'.\");" +
				"        return;\n" +
				"    }\n" +
				"    call g_map_put(share, \"child\", child);\n" +
				"    if (call g_array_size(cmd) > 1) {\n" +
				"        call g_array_remove(cmd, 0);\n" +
				"        var _args_ = {};\n" +
				"        call g_map_put(_args_, \"args\", cmd);\n" +
				"        call g_map_put(_args_, \"parent\", child);\n" +
				"        call g_create_user_process_args(parse_cmd_1, _args_);\n" +
				"    }\n" +
				"    var in = call g_create_pipe(\"PIPEIN#\" + parent);\n" +
				"    var out = call g_create_pipe(\"PIPEOUT#\" + pid);\n" +
				"    var f = func ~(ch) {\n" +
				"        call g_write_pipe(in, ch);\n" +
				"    };\n" +
				"    call g_read_pipe(out, f);\n" +
				"};\n" +
				"\n" +
				"var parse_cmd = func [\"PARSE\"] ~(cmd) {\n" +
				"    var pid = call g_get_pid();\n" +
				"    var exe = call g_array_get(cmd, 0);\n" +
				"    let exe = call g_string_trim(exe);\n" +
				"    var args = call g_string_split(exe, \" \");\n" +
				"    var exec = call g_array_get(args, 0);\n" +
				"    call g_array_remove(args, 0);\n" +
				"    var share = {};\n" +
				"    call g_map_put(share, \"args\", args);\n" +
				"    call g_map_put(share, \"parent\", pid);\n" +
				"    var path = \"/usr/p/\" + exec;\n" +
				"    var child = call g_load_user_x(path);\n" +
				"    if (child+1 == 0) {\n" +
				"        call g_printn(\"Cannot execute '\"+path+\"'.\");" +
				"        return;\n" +
				"    }\n" +
				"    call g_start_share(\"PID#\" + child, share);\n" +
				"    if (call g_array_size(cmd) > 1) {\n" +
				"        call g_array_remove(cmd, 0);\n" +
				"        var _args_ = {};\n" +
				"        call g_map_put(_args_, \"args\", cmd);\n" +
				"        call g_map_put(_args_, \"parent\", child);\n" +
				"        call g_create_user_process_args(parse_cmd_1, _args_);\n" +
				"    }\n" +
				"    var f = func ~(ch) -> call g_print(ch);\n" +
				"    var out = call g_create_pipe(\"PIPEOUT#\" + child);\n" +
				"    call g_read_pipe(out, f);\n" +
				"};\n" +
				"\n" +
				"// GET STDIO cmd\n" +
				"var get_input = func [\"INPUT\"] ~(this) {\n" +
				"    call g_print(\"$ \");\n" +
				"    var cmd = call g_stdin_read_line();\n" +
				"    let cmd = call g_string_trim(cmd);\n" +
				"    if (call g_string_length(cmd) == 0) {\n" +
				"        call g_printn(\"Error: no cmd\");\n" +
				"        return;\n" +
				"    }\n" +
				"    let cmd = call g_string_split(cmd, \"\\\\|\");\n" +
				"    call g_array_reverse(cmd);\n" +
				"    call parse_cmd(cmd);\n" +
				"    call g_create_user_process_args(this, this);\n" +
				"};\n" +
				"call g_create_user_process_args(get_input, get_input);\n";
	}
}