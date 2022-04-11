package gh.piotrus.napierdalanie.transformer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pl.memexurer.siurtransformer.loader.file.TransformerFile;

public class NumberTransformer implements DecryptionTransformer, Opcodes {
    public static AbstractInsnNode getNumber(int number) {
        if (number >= -1 && number <= 5) {
            return new InsnNode(number + 3);
        } else if (number >= -128 && number <= 127) {
            return new IntInsnNode(BIPUSH, number);
        } else if (number >= -32768 && number <= 32767) {
            return new IntInsnNode(SIPUSH, number);
        } else {
            return new LdcInsnNode(number);
        }
    }

    private static Integer doMath(int opcode, int first, int second) {
        return switch (opcode) {
            case IADD -> first + second;
            case ISUB -> first - second;
            case IMUL -> first * second;
            case IDIV -> first / opcode;
            case IREM -> first % second;
            case ISHL -> first << second;
            case ISHR -> first >> second;
            case IUSHR -> first >>> second;
            case IAND -> first & second;
            case IOR -> first | second;
            case IXOR -> first ^ second;
            default -> null;
        };
    }

    private static Long doMath(int opcode, long first, long second) {
        return switch (opcode) {
            case LADD -> first + second;
            case LSUB -> first - second;
            case LMUL -> first * second;
            case LDIV -> first / opcode;
            case LREM -> first % second;
            case LSHL -> first << second;
            case LSHR -> first >> second;
            case LUSHR -> first >>> second;
            case LAND -> first & second;
            case LOR -> first | second;
            case LXOR -> first ^ second;
            default -> null;
        };
    }

    private static Float doMath(int opcode, float first, float second) {
        return switch (opcode) {
            case FADD -> first + second;
            case FSUB -> first - second;
            case FMUL -> first * second;
            case FDIV -> first / opcode;
            case FREM -> first % second;
            default -> null;
        };
    }

    private static Double doMath(int opcode, double first, double second) {
        return switch (opcode) {
            case DADD -> first + second;
            case DSUB -> first - second;
            case DMUL -> first * second;
            case DDIV -> first / opcode;
            case DREM -> first % second;
            default -> null;
        };
    }


    public static AbstractInsnNode getLongInsn(long number) {
        if (number >= 0 && number <= 1) {
            return new InsnNode((int) (number + 9));
        } else {
            return new LdcInsnNode(number);
        }
    }

    public static Long getLongValue(AbstractInsnNode node) {
        if (node.getOpcode() >= Opcodes.LCONST_0
            && node.getOpcode() <= Opcodes.LCONST_1) {
            return (long) node.getOpcode() - 9;
        }
        if (node instanceof LdcInsnNode ldc) {
            if (ldc.cst instanceof Long) {
                return (long) ldc.cst;
            }
        }
        return null;
    }

    public static int getIntValue(AbstractInsnNode node) {
        if (node.getOpcode() >= Opcodes.ICONST_M1
                && node.getOpcode() <= Opcodes.ICONST_5)
            return node.getOpcode() - 3;
        if (node.getOpcode() == Opcodes.SIPUSH
                || node.getOpcode() == Opcodes.BIPUSH)
            return ((IntInsnNode) node).operand;
        if (node instanceof LdcInsnNode ldc) {
            if (ldc.cst instanceof Integer)
                return (int) ldc.cst;
        }
        return 0;
    }

    public static boolean isInteger(AbstractInsnNode ain) {
        if (ain == null) return false;
        if ((ain.getOpcode() >= Opcodes.ICONST_M1
                && ain.getOpcode() <= Opcodes.ICONST_5)
                || ain.getOpcode() == Opcodes.SIPUSH
                || ain.getOpcode() == Opcodes.BIPUSH)
            return true;
        if (ain instanceof LdcInsnNode ldc) {
            return ldc.cst instanceof Integer;
        }
        return false;
    }

    public static Float getFloatValue(AbstractInsnNode node) {
        if (node.getOpcode() >= Opcodes.FCONST_0
            && node.getOpcode() <= Opcodes.FCONST_2) {
            return (float) (node.getOpcode() - Opcodes.FCONST_0);
        }
        if (node instanceof LdcInsnNode ldc) {
            if (ldc.cst instanceof Float) {
                return (float) ldc.cst;
            }
        }
        return null;
    }

    public static Double getDoubleValue(AbstractInsnNode node) {
        if (node.getOpcode() >= Opcodes.DCONST_0
            && node.getOpcode() <= Opcodes.DCONST_1) {
            return (double) (node.getOpcode() - Opcodes.DCONST_0);
        }
        if (node instanceof LdcInsnNode ldc) {
            if (ldc.cst instanceof Double) {
                return (double) ldc.cst;
            }
        }
        return null;
    }

    public static AbstractInsnNode getFloatInsn(float number) {
        if (number == 0 || number == 1 | number == 2) {
            return new InsnNode((int) (number + Opcodes.FCONST_0));
        } else {
            return new LdcInsnNode(number);
        }
    }

    public static AbstractInsnNode getDoubleInsn(double number) {
        if (number == 0 || number == 1) {
            return new InsnNode((int) (number + Opcodes.DCONST_0));
        } else {
            return new LdcInsnNode(number);
        }
    }

    @Override
    public void decryptClass(TransformerFile file, ClassNode classNode) {
        classNode.methods.forEach(this::transform);
    }

    public void transform(MethodNode methodNode) {
        boolean modified;
        do {
            modified = false;

            for (AbstractInsnNode node : methodNode.instructions.toArray()) {
                if ((node.getOpcode() >= IADD && node.getOpcode() <= LXOR)) {
                    if (isInteger(node.getPrevious().getPrevious()) && isInteger(node.getPrevious())) {
                        int first = getIntValue(node.getPrevious().getPrevious());
                        int second = getIntValue(node.getPrevious());

                        Integer product = doMath(node.getOpcode(), first, second);
                        if (product != null) {
                            methodNode.instructions.remove(node.getPrevious().getPrevious());
                            methodNode.instructions.remove(node.getPrevious());
                            methodNode.instructions.set(node, getNumber(product));
                            modified = true;
                        }
                    } else {
                        {
                            Float first = getFloatValue(node.getPrevious().getPrevious());
                            Float second = getFloatValue(node.getPrevious());
                            if (first != null && second != null) {
                                Float product = doMath(node.getOpcode(), first, second);
                                if (product != null) {
                                    methodNode.instructions.remove(
                                        node.getPrevious().getPrevious());
                                    methodNode.instructions.remove(node.getPrevious());
                                    methodNode.instructions.set(node, getFloatInsn(product));
                                    modified = true;
                                    continue;
                                }
                            }
                        }

                        {
                            Long first = getLongValue(node.getPrevious().getPrevious());
                            Long second = getLongValue(node.getPrevious());
                            if (first != null && second != null) {
                                Long product = doMath(node.getOpcode(), first, second);
                                if (product != null) {
                                    methodNode.instructions.remove(node.getPrevious().getPrevious());
                                    methodNode.instructions.remove(node.getPrevious());
                                    methodNode.instructions.set(node, getLongInsn(product));
                                    modified = true;
                                    continue;
                                }
                            }
                        }

                        {
                            Double first = getDoubleValue(node.getPrevious().getPrevious());
                            Double second = getDoubleValue(node.getPrevious());
                            if (first != null && second != null) {
                                Double product = doMath(node.getOpcode(), first, second);
                                if (product != null) {
                                    methodNode.instructions.remove(node.getPrevious().getPrevious());
                                    methodNode.instructions.remove(node.getPrevious());
                                    methodNode.instructions.set(node, getDoubleInsn(product));
                                    modified = true;
                                }
                            }
                        }
                    }
                }
            }
        } while (modified);
    }
}
